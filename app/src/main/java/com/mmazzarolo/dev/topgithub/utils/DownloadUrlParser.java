package com.mmazzarolo.dev.topgithub.utils;

import android.content.Context;
import android.text.TextUtils;

import com.mmazzarolo.dev.topgithub.model.Repo;

import java.io.File;

/**
 * Created by Arison on 2017/2/9.
 * 下载地址解析工具类
 */
public class DownloadUrlParser {

    private static final String GITHUB_REPO_URL_BASE = "https://codeload.github.com/";
    private static final String ZIP_SUFFIX = ".zip";

    public static boolean parseGithubUrlAndDownload(Context context, String url) {
        String downloadUrl = parseGithubDownloadUrl(url);
        if (downloadUrl == null) return false;
        String repoName = getRepoName(url);
        Repo repo = new Repo(repoName
                , FileCache.getInstance().getRepoAbsolutePath(repoName), downloadUrl, true, 0);
//        Navigator.startDownloadNewRepoService(context, repo);
        return true;
    }
    
    

    /**
      * @desc:解析封装下载地址-github
      * @author：Arison on 2017/2/9
      */
    public static String parseGithubDownloadUrl(String url) {
        if (TextUtils.isEmpty(url)) return null;
        StringBuilder sb = new StringBuilder();
        String[] strings = url.split("/");
        if (strings.length < 5) return null;
        sb.append(GITHUB_REPO_URL_BASE);
        sb.append(strings[3]);
        sb.append("/");
        if (strings.length == 5) {
            if (strings[4].contains("?")) {
                String[] lastName = strings[4].split("\\?");
                sb.append(lastName[0]);
                sb.append("/");
            } else {
                sb.append(strings[4]);
                sb.append("/");
            }
            sb.append("zip/master");
            return sb.toString();
        }
        if (strings.length > 5) {
            sb.append(strings[4]);
            sb.append("/");
            if (strings.length >= 7 && strings[5].equals("tree")) {
                sb.append("zip/");
                if (strings[6].contains("?")) {
                    String[] lastName = strings[6].split("\\?");
                    sb.append(lastName[0]);
                } else {
                    sb.append(strings[6]);
                }
                return sb.toString();
            }
            sb.append("zip/master");
            return sb.toString();
        }
        return null;
    }
    
    
    /**
      * @desc:获取下载的项目名称
      * @author：Arison on 2017/2/9
      */
    public static String getRepoName(String url) {
        String[] strings = url.split("/");
        StringBuilder sb = new StringBuilder();
        sb.append(strings[4].split("\\.")[0]);
        if (strings.length >= 7 && strings[5].equals("tree")) {
            sb.append("(");
            if (strings[6].contains("?")) {
                String[] lastName = strings[6].split("\\?");
                sb.append(lastName[0]);
            } else {
                sb.append(strings[6]);
            }
            sb.append(")");
            return sb.toString();
        }
        return sb.toString();
    }

    
    public static File getRemoteRepoZipFileName(String repoName) {
        return new File(FileCache.getInstance().getCacheDir(), getRepoNameZip(repoName));
    }

    public static String getRepoNameZip(String name) {
        return name + ZIP_SUFFIX;
    }
}
