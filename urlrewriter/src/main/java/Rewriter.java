/**
 * Created by IntelliJ IDEA.
 * User: andrev
 * Date: 08.11.10
 * Time: 18.50
 * To change this template use File | Settings | File Templates.
 */
public class Rewriter {
    public String rewrite(String input) {
        if (input.startsWith("/guide")) {
            return handleGuide(input);
        }
        if (input.startsWith("/article")) {
            return handleArticle(input);
        }
        if(input.startsWith("/company"))
        {
            return handleCompany(input);
        }
        return input;
    }

    private String handleCompany(String input) {
        String[] urlParts = input.split("/");
        String country= urlParts[2];
        if(urlParts.length == 3)
           return String.format("/company?country=%s", country);
        String city= urlParts[3];
        return String.format("/company?country=%s&city=%s", country, city);
    }

    private String handleArticle(String input) {
        if(isCorrectArticleStructure(input)){
            if (input.indexOf("article") < 0)
                return input;
            String articleNo = input.substring(input.lastIndexOf('/') + 1);
            if (isNumber(articleNo))
                return "/article?id=" + articleNo;
        }
        return input;
    }

    private String handleGuide(String input) {
        String[] urlParts = input.split("/");
        if (!urlParts[3].equals("2008")) {
            return input;
        }
        urlParts[3] = "2009";
        String url = "";
        for (String part : urlParts) {
            url += part + "/";
        }
        if(input.endsWith("/"))
            return url;
        return url.substring(0, url.length() - 1);
    }

    private boolean isCorrectArticleStructure(String input) {
        return !(doesNotContainForwardSlash(input) || containsSeveralSlashes(input));
    }

    private boolean containsSeveralSlashes(String input) {
        String withoutArticle = input.replace("/article/", "");
        return !doesNotContainForwardSlash(withoutArticle);

    }

    private boolean isNumber(String articleNo) {
        try {
            Integer.parseInt(articleNo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean doesNotContainForwardSlash(String input) {
        return input.indexOf('/') < 0;
    }
}
