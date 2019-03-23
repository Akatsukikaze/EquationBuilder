package com.example.equationbuilder;

public class SimpleList<E> {
    private SimpleNode<E> head;
    private SimpleNode<E> tail;
    private int count;
    public SimpleList(){
        count = 0;
    }
    public SimpleList(E e){
        head = tail = new SimpleNode<E>(e);
        count = 1;
    }
    public void add(E e){
        tail = new SimpleNode<E>(e,tail);
        count++;
    }
    public boolean remove(){
        if(count>0){
            tail = tail.prev;
            tail.next = null;
            count--;
            return true;
        }else{
            return false;
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
    public int getCount(){
        return count;
    }
    public void clear(){
        head = tail = null;
        count = 0;
    }
}
