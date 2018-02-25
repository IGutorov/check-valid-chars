package info.igutorov.check.valid.chars;

import org.junit.Test;

import static org.junit.Assert.*;

public class CuttingCommentsTest {

    @Test
    public void lineLikeToDoCommentTest() {
        assertTrue(CuttingComments.lineLikeToDoComment("// TODO something"));
        assertTrue(CuttingComments.lineLikeToDoComment("//TODOsomething"));
        assertTrue(CuttingComments.lineLikeToDoComment("\u0009\u0009\u0020//\u0009TODO something"));
        assertTrue(CuttingComments.lineLikeToDoComment("// todo something"));
        assertTrue(CuttingComments.lineLikeToDoComment("\u0009//\u0009TODOsomething"));
        assertFalse(CuttingComments.lineLikeToDoComment("\u0009//\u0009TODsomething"));

        assertTrue(CuttingComments.isCommentedLine("// Task 2.1.b"));
    }

    @Test
    public void clearBlockCommentsTest() {
        assertNull(CuttingComments.clearBlockComments(null));
        assertEquals("", CuttingComments.clearBlockComments(""));
        String actual = CuttingComments.clearBlockComments("/**/DEF /*456*/\n\n88/*__*/");
        assertEquals( "DEF \n\n88", actual);
    }

}
