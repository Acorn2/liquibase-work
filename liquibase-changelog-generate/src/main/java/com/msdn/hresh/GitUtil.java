package com.msdn.hresh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/9/27 8:58 下午
 * @description
 */
public class GitUtil {

  public static String getGitUserName() {
    try {
      String cmd = "git config user.name";
      Process p = Runtime.getRuntime().exec(cmd);
      InputStream is = p.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = reader.readLine();
      p.waitFor();
      is.close();
      reader.close();
      p.destroy();
      return line;
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return "hresh";
  }
}
