package com.sword;

public class _52_RegularExpressionMatch {
    public boolean match(char[] string, char[] pattern)
    {
        if (string == null || pattern == null) {
            return false;
        }
        String p = new String(pattern);
        String str = new String(string);
        return isMatch(str,p);
    }
    public boolean isMatch(String str,String p){
        //都为空则返回true
        if(p.isEmpty())
            return str.isEmpty();
        //p长度为1，则s长度必须为1且满则s,p首字符相同或者是p为'.'
        if(p.length() == 1)
            return (str.length() == 1)&&(str.charAt(0) == p.charAt(0)||p.charAt(0) == '.');
        //当p第二个字符不是'*'时
        if(p.charAt(1) != '*'){
            //s为空则直接返回false
            if(str.isEmpty()) return false;
            //判断首字符是否相同(p首字符为.或者s和p首字符相同)且都从第二个字符进行递归判断
            return (str.charAt(0) == p.charAt(0)||p.charAt(0) == '.') &&
                    isMatch(str.substring(1),p.substring(1));
        }
        //当p第二个字符是'*'时
        //s不为空且和s和p的首字符匹配(首字符直接相等或者p首字符为.)，递归调用匹配s和去掉前两个字符的p，相当于把*看作是0
        //如果匹配则直接返回true,否则将s去掉首字符跟去掉前两个字符的p比较
        while(!str.isEmpty()&&(p.charAt(0) == str.charAt(0)|| p.charAt(0) == '.')){
            //即考虑*代表0次的情况
            if(isMatch(str,p.substring(2))) return true;
            //即考虑*不代表0的情况,让s去掉首字符接着跟p比较，上面p.substring(2)不会改变p的值
            str = str.substring(1);
        }
        //上面都行不通之后，这时候str已经去掉前面的一些字符了，然后接着跟*代表0的情况比较
        return  isMatch(str,p.substring(2));
    }
}
