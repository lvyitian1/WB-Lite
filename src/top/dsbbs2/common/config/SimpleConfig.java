
package top.dsbbs2.common.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SimpleConfig<T> implements IConfig<T>
{
  public File conf;
  public T con;
  public Gson g = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().setLenient().create();
  public String encode;
  public Class<T> clazz;
  public Constructor<T> cons;

  public SimpleConfig(final String loc, final String encode, final Class<T> clazz)
  {
    this.conf = new File(loc);
    this.encode = encode;
    this.clazz = clazz;
    try {
      this.cons = this.clazz.getDeclaredConstructor();
      this.cons.setAccessible(true);
    } catch (final Throwable e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void autoCreateNewFile() throws IOException
  {
    if (!this.conf.isFile()) {
      Optional.ofNullable(this.conf.getParentFile()).ifPresent(File::mkdirs);
      this.conf.createNewFile();
      this.initConfig();
    }
  }

  public void initConfig() throws IOException
  {
    try {
      this.con = this.getDefaultConfig();
    } catch (final Throwable e) {
      throw new RuntimeException(e);
    }
    this.saveConfig(false);
  }

  @Override
  public void loadConfig() throws IOException
  {
	  try {
    this.autoCreateNewFile();
    try (FileInputStream i = new FileInputStream(this.conf)) {
      final byte[] buf = new byte[i.available()];
      i.read(buf);
      this.con = this.g.fromJson(new String(buf, this.encode), this.clazz);
      if (this.con == null) {
        this.initConfig();
      }
    }
	  }catch (Throwable e) {
		e.printStackTrace();
		System.out.println("加载配置文件时出现了错误! 请检查, 如果实在无法检查出错误,可以尝试删除配置文件重新生成.");
	}
  }

  public T getDefaultConfig()
  {
    try {
      return this.cons.newInstance();
    } catch (final Throwable e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void saveConfig() throws IOException
  {
    this.saveConfig(true);
  }

  private void saveConfig(final boolean ac) throws IOException
  {
    if (ac) {
      this.autoCreateNewFile();
    }
    try (FileOutputStream i = new FileOutputStream(this.conf)) {
      i.write(this.g.toJson(this.con, this.clazz).getBytes(this.encode));
    }
  }

  @Override
  public T getConfig()
  {
    return this.con;
  }

}
