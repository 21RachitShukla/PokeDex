package rs21.pokedex;

import android.os.Parcel;
import android.os.Parcelable;

class Sprites implements Parcelable{
    String front_default;

    protected Sprites(Parcel in) {
        front_default = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(front_default);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Sprites> CREATOR = new Creator<Sprites>() {
        @Override
        public Sprites createFromParcel(Parcel in) {
            return new Sprites(in);
        }

        @Override
        public Sprites[] newArray(int size) {
            return new Sprites[size];
        }
    };

    public String getFront_default() {
        return front_default;
    }
}
