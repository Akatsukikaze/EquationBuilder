package com.example.equationbuilder;

public class SimpleList<E> {
    private SimpleNode<E> head;
    private SimpleNode<E> tail;
    private int count;
    public SimpleList(){
        head = tail = null;
        count = 0;
    }
    /*
    public SimpleList(E e){
        head = tail = new SimpleNode<E>(e);
        count = 1;
    }
    */
    public boolean isNotNull(){
        return count>0;
    }
    public void add(E e){
        if(head == null){
            tail = new SimpleNode<>(e,tail);
            head = tail;
        }else {
            tail.next = new SimpleNode<>(e, tail);
            tail = tail.next;
        }
        count++;
    }
    public boolean remove(){
        if(count>1){
            tail = tail.prev;
            tail.next = null;
            count--;
            return true;
        }else{
            if(count == 1) {
                head = tail = null;
                count = 0;
                return true;
            }else {
                return false;
            }
        }
    }
    public E get(int pos){
        if(pos < count){
            int i=0;
            SimpleNode<E> elem = head;
            while(i<pos){
                elem = elem.next;
                i++;
            }
            return elem.e;
        }else{
            return null;
        }
    }
    public boolean isExist(int pos){
        return pos<count;
    }
    public E getTail(){
        return tail.e;
    }
    public boolean set(int pos, E e){
        if(pos < 0 || pos >= count){
            return false;
        }else{
            int i = 0;
            SimpleNode<E> elem = head;
            while(i < pos){
                elem = elem.next;
                i++;
            }
            elem.e = e;
            return true;
        }
    }
    public boolean setTail(E e){
        if(count < 1){
            return false;
        }else{
            tail.e = e;
            return true;
        }
    }
    public int getCount(){
        return count;
    }
    public void clean(){
        head = tail = null;
        count = 0;
    }
}
