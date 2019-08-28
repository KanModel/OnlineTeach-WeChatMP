package me.kanmodel.july19.onlineteach;/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: KanModel
 * Date: 2019-07-08-15:00
 */

/**
 * @description: todo
 * @author: KanModel
 * @create: 2019-07-08 15:00
 */
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

public class BasicSample {
    public static void main(String[] args) {
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse("This is *Sparta*");
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
        System.out.println(html);
    }
}
