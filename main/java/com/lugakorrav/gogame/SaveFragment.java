package com.lugakorrav.gogame;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Григорий on 12.06.2016.
 */
public class SaveFragment extends Fragment {
    private  GoGame goGame;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setRetainInstance(true);
    }

    public GoGame getGoGame(){
        return  goGame;
    }

    public  void setGoGame(GoGame goGame){
        this.goGame = goGame;
    }
}
