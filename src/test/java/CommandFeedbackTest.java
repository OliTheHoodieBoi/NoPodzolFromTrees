import static org.junit.jupiter.api.Assertions.*;
import dev.hoodieboi.npfl.MainCommand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.junit.jupiter.api.Test;

public class CommandFeedbackTest {
    @Test
    public void OneValidArgument() {
        assertEquals(
            "<red><lang:command.unknown.argument><gray>\nabc a </gray><underlined>b</underlined><italic><lang:command.context.here>",
            MiniMessage.miniMessage().serialize(
                    MainCommand.incorrectArguments("abc", new String[] {"a", "b"}, 1)
            )
        );
    }

    @Test
    public void TwoValidArguments() {
        assertEquals(
                "<red><lang:command.unknown.argument><gray>\nabc a b </gray><underlined>c</underlined><italic><lang:command.context.here>",
                MiniMessage.miniMessage().serialize(
                        MainCommand.incorrectArguments("abc", new String[] {"a", "b", "c"}, 2)
                )
        );
    }

    @Test
    public void TwoInvalidArguments() {
        assertEquals(
                "<red><lang:command.unknown.argument><gray>\nabc a </gray><underlined>b c</underlined><italic><lang:command.context.here>",
                MiniMessage.miniMessage().serialize(
                        MainCommand.incorrectArguments("abc", new String[] {"a", "b", "c"}, 1)
                )
        );
    }


    @Test
    public void AllInvalidArguments() {
        assertEquals(
                "<red><lang:command.unknown.argument><gray>\nabc </gray><underlined>a b</underlined><italic><lang:command.context.here>",
                MiniMessage.miniMessage().serialize(
                        MainCommand.incorrectArguments("abc", new String[] {"a", "b"}, 0)
                )
        );
    }

    @Test
    public void LongArgument() {
        assertEquals(
                "<red><lang:command.unknown.argument><gray>\n...ghijklmnop </gray><underlined>b</underlined><italic><lang:command.context.here>",
                MiniMessage.miniMessage().serialize(
                        MainCommand.incorrectArguments("abc", new String[] {"abcdefghijklmnop", "b"}, 1)
                )
        );
    }

    @Test
    public void LongArguments() {
        assertEquals(
                "<red><lang:command.unknown.argument><gray>\n...ghijklmnop </gray><underlined>pneumonoultramicroscopicsilicovolcanoconiosis</underlined><italic><lang:command.context.here>",
                MiniMessage.miniMessage().serialize(
                        MainCommand.incorrectArguments("abc", new String[] {"abcdefghijklmnop", "pneumonoultramicroscopicsilicovolcanoconiosis"}, 1)
                )
        );
    }
}
