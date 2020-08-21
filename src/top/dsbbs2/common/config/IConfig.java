
package top.dsbbs2.common.config;

import java.io.IOException;

public interface IConfig<T>
{
  void autoCreateNewFile() throws IOException;

  void loadConfig() throws IOException;

  void saveConfig() throws IOException;

  T getConfig();
}
