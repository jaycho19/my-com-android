package com.dongfang.dl;

public interface OnDownloadListener {

	public void updateProcess(DownloadInfo dlInfo);

	public void finishDownload(DownloadInfo dlInfo);

	public void preDownload(DownloadInfo dlInfo);

	public void errorDownload(DownloadInfo dlInfo, Exception e);
}
