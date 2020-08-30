package top.whitecola.wblite;

import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

public class UUIDStorage{
  public ConcurrentHashMap<UUID,String> data=new ConcurrentHashMap<>();
}
