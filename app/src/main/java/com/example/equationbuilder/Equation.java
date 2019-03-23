package com.example.equationbuilder;

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
            return 0;
        }else{
            return 2;
        }
    }
    //0:success, 1:grammatical mistake, 2:variate not exist
    public int addVa(int tarVa){
        if(tarVa > vaCount){
            return 2;
        }else{
            if(struct.getTail()){
                return 1;
            }else{
                String str = nonVa.getTail();
                char last = str.charAt(str.length()-1);
                switch(last){
                    case '1':{}
                    case '2':{}
                    case '3':{}
                    case '4':{}
                    case '5':{}
                    case '6':{}
                    case '7':{}
                    case '8':{}
                    case '9':{}
                    case '0':{}
                    case ')':{}
                    case '.':
                        return 1;
                    default:
                        struct.add(true);
                        variate.add(tarVa);
                        return 0;
                }
            }
        }
    }
/*
    public boolean addChar(char input){
        if(struct.getTail()){
            switch (input){
                case '1':{}
                case '2':{}
                case '3':{}
                case '4':{}
                case '5':{}
                case '6':{}
                case '7':{}
                case '8':{}
                case '9':{}
                case '0':{}
                case '.':{}
                case '':{}

            }
        }
    }*/
}
