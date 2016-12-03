package timothyyudi.tryendless;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by root on 11/22/2016.
 */

public class DummyModel implements Parcelable{

    String title;
    String content;

    public DummyModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    protected DummyModel(Parcel in) {
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<DummyModel> CREATOR = new Creator<DummyModel>() {
        @Override
        public DummyModel createFromParcel(Parcel in) {
            return new DummyModel(in);
        }

        @Override
        public DummyModel[] newArray(int size) {
            return new DummyModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(content);
    }
}
