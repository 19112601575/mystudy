package com.example.exceptiontt.localeT;







import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;

import java.util.Locale;

//多语言
public class LocaleT {
    public static void main(String[] args) {
        Locale aDefault = Locale.getDefault();
        String language = aDefault.getLanguage();
        String str = "你好-hello";
//        Translate translate = TranslateOptions.getDefaultInstance().getService();
//        if ("zh".equals(language)){
//            translate.translate(str, Translate.TranslateOption.sourceLanguage("zh-cn"),
//                    Translate.TranslateOption.targetLanguage("zh-cn"));
//            System.out.println(translate);
//        }else if ("en".equals(language)){
//            translate.translate(str, Translate.TranslateOption.sourceLanguage("zh-cn"),
//                    Translate.TranslateOption.targetLanguage("en-us"));
//            System.out.println(translate);
//        }
        if ("zh".equals(language)){
            String string = str.substring(0,str.indexOf("-"));
            System.out.println(string);
        }else if ("en".equals(language)){
            String string = str.substring(str.indexOf("-")+1);
            System.out.println(string);
        }
    }
}
