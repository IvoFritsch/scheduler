/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 0186779
 */
public class Teste implements Comparable<Teste>{
    int i;
    
    
    public Teste(int i) {
        this.i = i;
    }

    
    @Override
    public int compareTo(Teste o) {
        return i - o.i;
    }
    
}
