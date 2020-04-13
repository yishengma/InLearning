package com.inlearning.app.common.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {
    private Context context;
    private static FileUtil util = null;

    private FileUtil() {

    }

    public static FileUtil getInstance() {
        if (util == null) {
            util = new FileUtil();
        }
        return util;
    }

    public static List<Map<String, String>> readExcel(String path, String columns[], CellType[] cellTypes) {
        File file = new File(path);
        String filePath = file.getAbsolutePath();
        Sheet sheet = null;
        Row row = null;
        Row rowHeader = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                wb = new XSSFWorkbook(is);
            } else {

                wb = null;
            }
            if (wb != null) {
                // 用来存放表中数据
                list = new ArrayList<Map<String, String>>();
                // 获取第一个sheet
                sheet = wb.getSheetAt(0);
                // 获取最大行数
                int rownum = sheet.getPhysicalNumberOfRows();
                // 获取第一行
                rowHeader = sheet.getRow(0);
                row = sheet.getRow(0);
                // 获取最大列数
                int colnum = row.getPhysicalNumberOfCells();
                for (int i = 1; i < rownum; i++) {
                    Map<String, String> map = new LinkedHashMap<String, String>();
                    row = sheet.getRow(i);
                    if (row != null) {
                        for (int j = 0; j < colnum; j++) {
                            if (columns[j].equals(getCellFormatValue(rowHeader.getCell(j)))) {
                                Cell cell = row.getCell(j);
                                cell.setCellType(cellTypes[j]);
                                cellData = (String) getCellFormatValue(cell);
                                map.put(columns[j], cellData);
                                /*DecimalFormat df = new DecimalFormat("#");
                                System.out.println(    df.format(cellData));*/
                            }
                        }
                    } else {
                        break;
                    }
                    list.add(map);
                }
            }
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return list;
    }

    /**
     * 获取单个单元格数据
     *
     * @param cell
     */

    public static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            // 判断cell类型
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {
                    DecimalFormat df = new DecimalFormat("0");
                    cellValue = df.format(cell.getNumericCellValue());
                    Log.e("ethan", "number" + cellValue);
                    break;
                }
                case Cell.CELL_TYPE_FORMULA: {
                    // 判断cell是否为日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        // 数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    Log.e("ethan", "date" + cellValue);
                    break;
                }
                case Cell.CELL_TYPE_STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    Log.e("ethan", "string" + cellValue);
                    break;
                }

                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    public static String getChooseFileResultPath(Context context, Uri uri) {
        String chooseFilePath = null;
        if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
            chooseFilePath = uri.getPath();
            Toast.makeText(context, chooseFilePath, Toast.LENGTH_SHORT).show();
            return chooseFilePath;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
            chooseFilePath = saveFile2CachePath(context, uri);
        } else {//4.4以下下系统调用方法
            chooseFilePath = getRealPathFromURI(context, uri);
        }
        return chooseFilePath;
    }

    private static String getRealPathFromURI(Context context, Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    private static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];

                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);

            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);

            }

        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);

        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            uri.getPath();

        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getValidExternalDirectoryPath() {
        //        Context context = AppRunTime.getInstance().getApplication().getApplicationContext();
        //        String path = context.getExternalFilesDir(getAppDirectoryName()).getAbsolutePath();
        String path = getExternalDirectoryPath() + "/Android/data/com.inlearning.app/files/inlearning";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public static String getExternalDirectoryPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static String saveFile2CachePath(Context context, Uri contentUri) {
        String fileName = getFileName(contentUri);
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        File copyFile = new File(getValidExternalDirectoryPath() + File.separator + fileName);
        if (copyFile(context, contentUri, copyFile)) {
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    private static String getFileName(Uri uri) {
        String fileName = "";
        if (uri == null) {
            return fileName;
        }
        String path = uri.getPath();
        if (path == null) {
            return fileName;
        }
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    private static boolean copyFile(Context context, Uri srcUri, File dstFile) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        boolean result;
        try {
            inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) {
                return false;
            }
            outputStream = new FileOutputStream(dstFile);
            result = copyStream(inputStream, outputStream);
        } catch (Exception e) {
            result = false;
        } finally {
            close(inputStream);
            close(outputStream);
        }
        return result;
    }

    private static boolean copyStream(InputStream input, OutputStream output) {
        byte[] buffer = new byte[1024 * 2];
        boolean result = true;
        BufferedInputStream in = new BufferedInputStream(input, 1024 * 2);
        BufferedOutputStream out = new BufferedOutputStream(output, 1024 * 2);
        int len;
        try {
            while ((len = in.read(buffer, 0, 1024 * 2)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (IOException e) {
            result = false;
        } finally {
            close(out);
            close(in);
        }
        return result;
    }

    private static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
        }
    }
}
