package chat;

import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.collection.Seq;
import nz.sodium.Cell;
import nz.sodium.Stream;
import nz.sodium.StreamSink;

public class Chat {

    public final StreamSink<Session> joins = new StreamSink<>();
    public final Stream<Message> output;

    Chat() {
        Cell<Map<String, Session>> sessions = joins
                .accum(HashMap.empty(), (session, map) -> map.put(session.nick, session));
        Cell<Stream<Message>> mergedMessages =
                sessions.map(m -> mergeStreams(m.values().map(session -> session.messages)));
        Stream<Message> messages = Cell.switchS(mergedMessages);
        this.output = messages
                .orElse(joins.map(session -> new Message(session.nick, "joins")));
    }

    public static <T> Stream<T> mergeStreams(Seq<Stream<T>> streams) {
        Stream<T> acc = new Stream<>();
        for (Stream<T> stream : streams) {
            acc = acc.orElse(stream);
        }
        return acc;
    }

}
