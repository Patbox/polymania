package eu.pb4.polymania;

import eu.pb4.placeholders.api.node.DirectTextNode;
import eu.pb4.placeholders.api.node.LiteralNode;
import eu.pb4.placeholders.api.node.TextNode;
import eu.pb4.placeholders.api.node.parent.ParentNode;
import eu.pb4.placeholders.api.parsers.NodeParser;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public record VersionParser() implements NodeParser {
    public static final Pattern URL_REGEX = Pattern.compile("(\\([-a-zA-Z0-9@:%._\\+~#=]+( -> )[-a-zA-Z0-9@:%._\\+~#=]+\\))");

    @Override
    public TextNode[] parseNodes(TextNode node) {
        if (node instanceof LiteralNode literalNode) {
            var input = literalNode.value();
            var list = new ArrayList<TextNode>();

            Matcher matcher = URL_REGEX.matcher(input);
            int currentPos = 0;
            int currentEnd = input.length();


            while (matcher.find()) {
                if (currentEnd <= matcher.start()) {
                    break;
                }

                String betweenText = input.substring(currentPos, matcher.start());
                var link = matcher.group();

                if (!betweenText.isEmpty()) {
                    list.add(new LiteralNode(betweenText));
                }

                list.add(new DirectTextNode(Text.literal(link).formatted(Formatting.GRAY)));

                currentPos = matcher.end();
            }

            if (currentPos < currentEnd) {
                String restOfText = input.substring(currentPos, currentEnd);
                if (!restOfText.isEmpty()) {
                    list.add(new LiteralNode(restOfText));
                }
            }

            return list.toArray(new TextNode[0]);
        } else if (node instanceof ParentNode parentNode) {
            var list = new ArrayList<TextNode>();

            for (var child : parentNode.getChildren()) {
                list.addAll(List.of(this.parseNodes(child)));
            }

            return new TextNode[] { parentNode.copyWith(list.toArray(new TextNode[0])) };
        }

        return new TextNode[] { node };
    }
}
