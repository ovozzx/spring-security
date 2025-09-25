package com.ktdsuniversity.edu.file.vo;

/**
 * @TableName FILE
 * @TableComment null
 */
public class FileVO {

    /**
     * @ColumnName FILE_ID
     * @ColumnType VARCHAR2(20)
     * @ColumnComment null
     */
    private String fileId;

    /**
     * @ColumnName FILE_GROUP_ID
     * @ColumnType VARCHAR2(20)
     * @ColumnComment null
     */
    private String fileGroupId;

    /**
     * @ColumnName FILE_SIZE
     * @ColumnType NUMBER(36, 0)
     * @ColumnComment null
     */
    private long fileSize;

    /**
     * @ColumnName FILE_DISPLAY_NAME
     * @ColumnType VARCHAR2(100)
     * @ColumnComment null
     */
    private String fileDisplayName;

    /**
     * @ColumnName FILE_NAME
     * @ColumnType VARCHAR2(100)
     * @ColumnComment null
     */
    private String fileName;

    /**
     * @ColumnName FILE_PATH
     * @ColumnType VARCHAR2(2000)
     * @ColumnComment null
     */
    private String filePath;

    /**
     * @ColumnName FILE_DOWNLOAD_COUNT
     * @ColumnType NUMBER(36, 0)
     * @ColumnComment null
     */
    private int fileDownloadCount;

    /**
     * @ColumnName FILE_TYPE
     * @ColumnType VARCHAR2(10)
     * @ColumnComment null
     */
    private String fileType;

    public String getFileId() {
        return this.fileId;
    }
    
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    
    public String getFileGroupId() {
        return this.fileGroupId;
    }
    
    public void setFileGroupId(String fileGroupId) {
        this.fileGroupId = fileGroupId;
    }
    
    public long getFileSize() {
        return this.fileSize;
    }
    
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    
    public String getFileDisplayName() {
        return this.fileDisplayName;
    }
    
    public void setFileDisplayName(String fileDisplayName) {
        this.fileDisplayName = fileDisplayName;
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFilePath() {
        return this.filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public int getFileDownloadCount() {
        return this.fileDownloadCount;
    }
    
    public void setFileDownloadCount(int fileDownloadCount) {
        this.fileDownloadCount = fileDownloadCount;
    }
    
    public String getFileType() {
        return this.fileType;
    }
    
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    
    @Override
    public String toString() {
        return "FileVO(fileId: " + fileId + ", fileGroupId: " + fileGroupId + ", fileSize: " + fileSize + ", fileDisplayName: " + fileDisplayName + ", fileName: " + fileName + ", filePath: " + filePath + ", fileDownloadCount: " + fileDownloadCount + ", fileType: " + fileType + ", )";
    }
}