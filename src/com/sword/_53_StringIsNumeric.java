package com.sword;

public class _53_StringIsNumeric {
    public boolean isNumeric(char[] str) {
        //String s = new String(str);
        //return s.matches("[\\+\\-]?\\d*(\\.\\d+)?([eE][\\+\\-]?\\d+)?");
        //利用系统自己的检查机制
        //转换出现异常时返回false
        /*try {
            double number = Double.parseDouble(s);
        }catch(NumberFormatException e) {
            return false;
        }
        return true;
        */

        //硬核解法直接逐个逐个字符判断
        //标记符号、小数点、e是否出现过
        boolean sign=false,decimal = false,hasE = false,hasNum = false;
        for(int i = 0;i<str.length;i++) {
            //判断e前面是否有数字出现
            if(str[i]>='0'&& str[i]<='9') {
                hasNum = true;
            }
            /*
             * 1. 判断'e'和'E'
             */
            //e后面一定要接数字
            if(str[i] == 'e' ||  str[i] == 'E') {
                if(!hasNum || i == str.length -1) return false;//e后面一定要有数字
                if(hasE) return false;//e只能存在一个
                hasE = true;
                /*
                 * 2. 判断'+'和'-'
                 */
            }else if(str[i] == '+' || str[i] == '-') {
                //第二次出现+或-时，必须是紧跟在e后面
                if(sign&&str[i-1] != 'e' &&str[i-1]!='E') return false;
                //第一次出现+或-时，如果不是在字符串开头则一定要在e后面
                if (!sign && i > 0 && str[i - 1] != 'e' && str[i - 1] != 'E') return false;
                sign = true;
                /*
                 * 3. 判断'.'
                 */
            }else if(str[i] == '.') {
                //e后面不能有小数点
                //小数点不能出现两次，当hasE=false时判断deciaml，如果前面已经有小数点即decimal=true则返回false
                if(hasE || decimal) return false;
                decimal = true;
                /*
                 * 4. 判断是否为数字
                 */
            }else if(str[i]<'0'||str[i]>'9') {
                return false;
            }
        }
        return true;
    }
}
