/*

1) /article/$1 into /article?id=$1 ($1 is always a number!)
(e.g. /article/512 into /article?id=512)

2) /guide/$1/2008/$2 into /guide/$1/2009/$2 ($2 can be empty)
(e.g. /guide/srv/2008/x12_3/index.html into /guide/srv/2009/x12_3/index.html)

3) /company/$1/$2 into /company?country=$1&city=$2 ($2 can be empty)
(e.g. /company/usa/newyork into /company?country=usa&city=newyork)

*/

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RewriterTest {

    private Rewriter rewriter;

    @Before
    public void setup(){
        rewriter = new Rewriter();
    }
    @Test
    public void testNoMatch() {
        assertEquals("test", rewriter.rewrite("test"));
    }
    @Test
    public void testReplaceNumberWithId(){
        assertEquals("/article?id=512", rewriter.rewrite("/article/512"));
    }
    @Test
    public void testDoesNotReplaceString(){
        assertEquals("/article/hei", rewriter.rewrite("/article/hei"));
    }
    @Test
    public void testDoesNotReplaceNonArticle(){
        assertEquals("/floff/123", rewriter.rewrite("/floff/123"));
    }
     @Test
    public void testDoNotReplaceArticleWhenSomethingInBetween(){
        assertEquals("/article/heisann/123", rewriter.rewrite("/article/heisann/123"));
    }
    @Test
    public void testGuideWithEmptyLastArg(){
        assertEquals("/guide/site/2009", rewriter.rewrite("/guide/site/2008"));
    }
    @Test
    public void testGuideWithLastArg(){
        assertEquals("/guide/site/2009/test", rewriter.rewrite("/guide/site/2008/test"));
    }
    @Test
    public void testGuideWithEmptyLastArgTrailingSlash(){
        assertEquals("/guide/site/2009/", rewriter.rewrite("/guide/site/2008/"));
    }

    @Test
    public void testGuideWithWrongYear(){
        assertEquals("/guide/site/2007/", rewriter.rewrite("/guide/site/2007/"));
    }
    @Test
    public void testGuideWithLastArgTrailingSlash(){
        assertEquals("/guide/site/2009/test/", rewriter.rewrite("/guide/site/2008/test/"));
    }
    @Test
    public void testGuideWithLastArgNoTrailingSlash(){
        assertEquals("/guide/site/2009/test", rewriter.rewrite("/guide/site/2008/test"));
    }
    @Test
    public void testGuideWithLastSeveral2008(){
        assertEquals("/guide/2008/2009/test", rewriter.rewrite("/guide/2008/2008/test"));
    }
    @Test
    public void testCompanyWithCity(){
        assertEquals("/company?country=usa&city=newyork", rewriter.rewrite("/company/usa/newyork"));
    }
    @Test
    public void testCompanyWithoutCity(){
        assertEquals("/company?country=usa", rewriter.rewrite("/company/usa/"));
    }
    @Test
    public void testCompanyWithoutCityAndWithoutTrailingSlash(){
        assertEquals("/company?country=usa", rewriter.rewrite("/company/usa"));
    }
    @Test
    public void testCompanyWithCityAndTrailingSlash(){
        assertEquals("/company?country=usa&city=newyork", rewriter.rewrite("/company/usa/newyork/"));
    }
}
