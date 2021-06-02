package com.udacity.jwdnd.course1.cloudstorage.util;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileFactory {
    public static List<File> createFileList() {

        byte[] fileData = new byte[0];
        try {
            fileData = FileResourceHelper.getFileInByteArray("/myfile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        var fileSize = fileData.length + "";
        var myTestedFile = File.from("myFile", "String", fileSize, fileData);
        var file = new File();
        file.setFileId(100);
        file.setUserId(142);
        file.setFileName("my_File_UzerZ_1.txt");
        file.setContentType("text/plain");
        file.setFileData(myTestedFile.getFileData());

        var list = new ArrayList<File>();
        list.add(file);
//        list.add(file);
//        list.add(file);
        return list;
    }

    public static File createFile() {

        byte[] fileData = new byte[0];
        try {
            fileData = FileResourceHelper.getFileInByteArray("/myfile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        var fileSize = fileData.length + "";
        var myTestedFile = File.from("myFile", "String", fileSize, fileData);
        var file = new File();
        file.setFileId(100);
        file.setUserId(142);
        file.setFileName("my_File_UzerZ_1.txt");
        file.setContentType("text/plain");
        file.setFileData(myTestedFile.getFileData());
        return file;
    }

    public static File createFile2(int userId) {

        byte[] fileData = new byte[0];
        try {
            fileData = FileResourceHelper.getFileInByteArray("/myfile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        var fileSize = fileData.length + "";
        var myTestedFile = File.from("myFile", "String", fileSize, fileData);
        var file = new File();
        file.setFileId(100);
        file.setUserId(userId);
        file.setFileName("my_File_UzerZ_1.txt");
        file.setContentType("text/plain");
        file.setFileData(myTestedFile.getFileData());
        return file;
    }
}
