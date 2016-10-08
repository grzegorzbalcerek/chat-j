package chat;

import nz.sodium.Cell;
import nz.sodium.Stream;
import nz.sodium.StreamSink;

public class Chat {

    public final StreamSink<Session> joins = new StreamSink<>();
    public final Stream<Message> output;

    Chat() {
        Cell<Stream<Message>> mergedMessages =
                joins.accum(new Stream<>(), (session, merged) -> session.messages.orElse(merged));
        Stream<Message> messages = Cell.switchS(mergedMessages);
        this.output = messages
                .orElse(joins.map(session -> new Message(session.nick, "joins")));
    }

}
