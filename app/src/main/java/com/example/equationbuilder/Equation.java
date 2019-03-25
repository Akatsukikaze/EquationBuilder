package com.example.equationbuilder;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class Equation {
    private int vaCount;
    private SimpleList<Integer> variate;
    private SimpleList<String> nonVa;
    private SimpleList<Boolean> struct;//true for va, false for nonVa
    public Equation(){
        vaCount = 0;
        variate = new SimpleList<>();
        nonVa = new SimpleList<>();
        struct = new SimpleList<>();
    }
    public Equation(String equation){
        vaCount = 0;
        variate = new SimpleList<>();
        nonVa = new SimpleList<>();
        struct = new SimpleList<>();

        String regex = "a";
        String cutted[] = equation.split(regex);

        int count = cutted.length;
        boolean state = equation.charAt(0) == 'a';
        int i = 0;
        while(i < count){
            if(state){
                struct.add(true);
                state = false;
                variate.add(Integer.parseInt(cutted[i]));
            }else{
                struct.add(false);
                state=true;
                nonVa.add(cutted[i]);
            }
            i++;
        }
    }
    public int newVa(){
        vaCount++;
        return vaCount;
    }
    //0:success, 1:target variate in use 2:target variate not exist
    public int removeVa(int tarVa){
        if(tarVa > 0 && tarVa <= vaCount){
            int nodeCount = variate.getCount();
            int temp;
            int i = 0;
            while(i < nodeCount){
                temp = variate.get(i);
                if(temp == tarVa){
                    return 1;
                }
                i++;
            }
            i = 0;
            while(i < variate.getCount()){
                int tar = variate.get(i);
                if(tar > tarVa){
                    variate.set(i,tar-1);
                }
                i++;
            }
            vaCount--;
            return 0;
        }else{
            return 2;
        }
    }
    public int getVaCount(){
        return vaCount;
    }
    public SimpleList<Boolean> getStruct(){
        return struct;
    }
    public int getVaByPos(int pos){
        return variate.get(pos);
    }
    public String getNonByPos(int pos){
        return nonVa.get(pos);
    }
    //0:success, 1:grammatical mistake, 2:variate not exist
    public int addVa(int tarVa){
        if(tarVa > vaCount){
            return 2;
        }else{
            if(struct.isNotNull()) {
                if (struct.getTail()) {
                    return 1;
                } else {
                    String str = nonVa.getTail();
                    char last = str.charAt(str.length() - 1);
                    switch (last) {
                        case '1': return 1;
                        case '2': return 1;
                        case '3': return 1;
                        case '4': return 1;
                        case '5': return 1;
                        case '6': return 1;
                        case '7': return 1;
                        case '8': return 1;
                        case '9': return 1;
                        case '0': return 1;
                        case ')': return 1;
                        case '.': return 1;
                        default:
                            struct.add(true);
                            variate.add(tarVa);
                            return 0;
                    }
                }
            }else{
                struct.add(true);
                variate.add(tarVa);
                return 0;
            }
        }
    }
    //true for success, false for not legal
    public boolean addChar(char input){
        if(struct.isNotNull()) {
            if (struct.getTail()) {
                switch (input) {
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                    case '0':
                    case '.':
                    case '(': return false;
                    default:
                        struct.add(false);
                        nonVa.add(""+input);
                        return true;
                }
            }else{
                char lastChar;
                String lastStr = nonVa.getTail();
                lastChar = lastStr.charAt(lastStr.length()-1);
                switch(input){
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                    case '0':
                        if(lastChar == ')'){
                            return false;
                        }else{
                            nonVa.setTail(lastStr+input);
                            return true;
                        }
                    case '+':
                    case '*':
                    case '/':
                        if(lastChar == '+' ||
                                lastChar == '-' ||
                                lastChar == '*' ||
                                lastChar == '/' ||
                                lastChar == '(' ||
                                lastChar == '.'){
                            return false;
                        }else{
                            nonVa.setTail(lastStr+input);
                            return true;
                        }
                    case '-':
                        if(lastChar == '-' || lastChar == '.'){
                            return false;
                        }else{
                            nonVa.setTail(lastStr+input);
                            return true;
                        }
                    case '(':
                        if(lastChar == '+' ||
                                lastChar == '-' ||
                                lastChar == '*' ||
                                lastChar == '/' ||
                                lastChar == '('){
                            nonVa.setTail(lastStr+input);
                            return true;
                        }else{
                            return false;
                        }
                    case ')':
                        if(lastChar == '+' ||
                                lastChar == '-' ||
                                lastChar == '*' ||
                                lastChar == '/' ||
                                lastChar == '.'){
                            return false;
                        }else{
                            nonVa.setTail(lastStr+input);
                            return true;
                        }
                    case '.':
                        if(lastChar == ')' ||
                                lastChar == '.'){
                            return false;
                        }else{
                            if(lastChar == '(' ||
                                    lastChar == '+' ||
                                    lastChar == '-' ||
                                    lastChar == '*' ||
                                    lastChar == '/'){
                                nonVa.setTail(lastStr+'0'+input);
                                return true;
                            }else{
                                nonVa.setTail(lastStr+input);
                                return true;
                            }
                        }
                }
            }
        }else{
            switch (input){
                case '+':
                case '*':
                case '/':
                case ')':
                    return false;
                case '.':
                    struct.add(false);
                    nonVa.add("0"+input);
                    break;
                default:
                    struct.add(false);
                    nonVa.add(""+input);
                    break;
            }
            return true;
        }
        return false;
    }
    public void cleanAll(){
        struct.clean();
        variate.clean();
        nonVa.clean();
    }
    public void backspace(){
        if(struct.isNotNull()) {
            if (struct.getTail()) {
                struct.remove();
                variate.remove();
            } else {
                String str = nonVa.getTail();
                int length = str.length();
                if(length > 1){
                    str = str.substring(0,length-1);
                    nonVa.setTail(str);
                }else{
                    struct.remove();
                    nonVa.remove();
                }
            }
        }
    }
    public String makeEquation(){
        int i = 0;
        int vCount = 0;
        int nCount = 0;
        String out = "";
        while(struct.isExist(i)){
            if(struct.get(i)){
                out = out + "a" + variate.get(vCount) + "a";
                vCount++;
            }else{
                out = out + nonVa.get(nCount);
                nCount++;
            }
            i++;
        }
        float[] vas = new float[vaCount];
        i = 0;
        while(i < vaCount) {
            vas[i] = 1;
            i++;
        }
        String test = executeEquation(out,vaCount,vas);
        if(test != null) {
            return out;
        }else{
            return null;
        }
    }
    public static String executeEquation(String equation, int count_va, float... va){
        int i = 0;
        while(i<count_va){
            String regex = "a"+(i+1)+"a";
            String replace = ""+va[i];
            equation = equation.replaceAll(regex, replace);
            i++;
        }
        String res = null;
        Context cx = Context.enter();
        cx.setOptimizationLevel(-1);
        try
        {
            Scriptable scope = cx.initStandardObjects();
            Object result = cx.evaluateString(scope, equation, null, 1, null);
            res = Context.toString(result);
        }catch (Exception e){
            return null;
        }
        finally
        {
            Context.exit();
        }
        return res;
    }
}
