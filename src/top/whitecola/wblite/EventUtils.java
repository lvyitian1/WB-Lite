package top.whitecola.wblite;

import java.lang.reflect.Method;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.internal.EventInternalJvmKt;
import top.dsbbs2.common.lambda.INoThrowsRunnable;

public final class EventUtils {
private EventUtils() {}
@SuppressWarnings("unchecked")
public static <T extends Event> void registerEvents(Object o)
{
	System.out.println("§b开始订阅事件");
	INoThrowsRunnable.invoke(()->{
		Method[] methods=o.getClass().getDeclaredMethods();
		for(Method i:methods)
		{
			i.setAccessible(true);
			EventHandler e=i.getAnnotation(EventHandler.class);
			if(e!=null)
			{
				if(i.getReturnType().equals(void.class) || i.getReturnType().equals(Void.class))
				{
					if(i.getParameters().length==1)
					{
						if(Event.class.isAssignableFrom(i.getParameterTypes()[0]))
						{
							EventInternalJvmKt._subscribeEventForJaptOnly((Class<T>)i.getParameterTypes()[0], WBLite.instance.bot, new IEventHandler<T>() {

								@Override
								public void accept(T t) {
									INoThrowsRunnable.invoke(()->i.invoke(o, t));
								}
								
							});
						}
					}
				}
			}
		}
	});
	System.out.println("§b已订阅事件,开始登陆,这可能需要一段时间(请注意登陆弹窗)!");
}
}
