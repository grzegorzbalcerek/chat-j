package chat;

import nz.sodium.Stream;
import nz.sodium.StreamSink;

class Session {
    public Stream<String> output;
    public StreamSink<String> input = new StreamSink<>();
    Session(String nick, Chat chat) {
        this.output = input;
    }

}
