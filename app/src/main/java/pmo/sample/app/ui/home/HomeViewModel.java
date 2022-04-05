package pmo.sample.app.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {


    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Tekan tombol di bawah untuk melihat data mahasiswa");
    }

    public LiveData<String> getText() {
        return mText;
    }


}