package ir.asandiag.pageindicatorview.draw.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;

public class PositionSavedState extends View.BaseSavedState {

    private int selectedPosition;
    private int selectingPosition;
    private int lastSelectedPosition;

    public PositionSavedState(Parcelable superState) {
        super(superState);
    }

    public static final Creator<PositionSavedState> CREATOR = new Creator<PositionSavedState>() {
        @NonNull
        public PositionSavedState createFromParcel(@NonNull Parcel in) {
            return new PositionSavedState(in);
        }

        @NonNull
        public PositionSavedState[] newArray(int size) {
            return new PositionSavedState[size];
        }
    };

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectingPosition() {
        return selectingPosition;
    }

    public void setSelectingPosition(int selectingPosition) {
        this.selectingPosition = selectingPosition;
    }

    public int getLastSelectedPosition() {
        return lastSelectedPosition;
    }

    public void setLastSelectedPosition(int lastSelectedPosition) {
        this.lastSelectedPosition = lastSelectedPosition;
    }

    private PositionSavedState(@NonNull Parcel in) {
        super(in);
        this.selectedPosition = in.readInt();
        this.selectingPosition = in.readInt();
        this.lastSelectedPosition = in.readInt();
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeInt(this.selectedPosition);
        out.writeInt(this.selectingPosition);
        out.writeInt(this.lastSelectedPosition);
    }
}
