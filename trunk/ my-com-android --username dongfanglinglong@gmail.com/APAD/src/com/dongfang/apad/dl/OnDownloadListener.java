package com.dongfang.apad.dl;

import com.dongfang.apad.util.DFException;


public interface OnDownloadListener {

	public void updateProcess(DownloadInfo dlInfo);

	public void finishDownload(DownloadInfo dlInfo);

	public void preDownload(DownloadInfo dlInfo);

	public void errorDownload(DownloadInfo dlInfo, DFException e);
}
