
package top.dsbbs2.common.closure;

public class Reference<T>
{
  public volatile T value;
  public Reference(T value)
  {
    this.value=value;
  }
}
