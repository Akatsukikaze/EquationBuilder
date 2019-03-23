package com.example.equationbuilder;

public class SimpleNode<E> {
    E e;
    SimpleNode<E> next;
    SimpleNode<E> prev;
    public SimpleNode(E elem){
        e = elem;
        prev = next = null;
    }
    public SimpleNode(E elem, SimpleNode<E> prevN){
        e = elem;
        prev = prevN;
        next = null;
    }
}
