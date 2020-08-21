package top.whitecola.wblite;

import java.util.function.Consumer;

import net.mamoe.mirai.event.Event;

public interface IEventHandler<T extends Event> extends Consumer<T>{
}
