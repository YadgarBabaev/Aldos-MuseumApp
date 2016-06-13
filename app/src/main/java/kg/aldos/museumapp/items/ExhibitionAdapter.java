package kg.aldos.museumapp.items;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kg.aldos.museumapp.dbClasses.DBHelper;
import kg.aldos.museumapp.fragments.ExhibitionFragment;

public class ExhibitionAdapter extends FragmentPagerAdapter{

    ArrayList<Exhibition> arrayList = new ArrayList<>();
    public Context context;


    public ExhibitionAdapter(Context context, FragmentManager fm){
        super(fm);
        this.context = context;
        setData();
    }

    @Override
    public Fragment getItem(int position) {
        return ExhibitionFragment.newInstance(position, arrayList);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public void setData() {
        DBHelper db = new DBHelper(context);
        int[] ids = db.getIds("exhibition");
//        ids = reverseArray(ids);
        for (int id : ids){
            Exhibition object = db.getExhibition(id);
            arrayList.add(object);
        }
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//        ViewHolder holder;
//
//        if(view == null) {
//            LayoutInflater inflater = activity.getLayoutInflater();
//            view = inflater.inflate(R.layout.exhibition_item, null);
//            holder = new ViewHolder();
//            holder.title = (TextView) view.findViewById(R.id.title);
//            holder.text = (TextView) view.findViewById(R.id.text);
//            holder.image = (ImageView) view.findViewById(R.id.imageView);
//            view.setTag(holder);
//        }
//        else
//            holder = (ViewHolder) view.getTag();
//
//        if (Data.size() <= 0) {
//            holder.text.setGravity(View.TEXT_ALIGNMENT_CENTER);
//            holder.text.setText("\n No Data \n");
//        } else {
//            object = Data.get(position);
//            holder.title.setText(object.getTitle());
//            holder.text.setText(object.getText());
//
//            Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(object.getImage()));
//            Bitmap imageRounded = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
//            Canvas canvas = new Canvas(imageRounded);
//            Paint paint = new Paint();
//            paint.setAntiAlias(true);
//            paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
//            canvas.drawRoundRect((new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight())), 100, 100, paint);
//            holder.image.setImageBitmap(bitmap);
//        }
//        return view;
//    }
//
//    static class ViewHolder{
//        public TextView title;
//        public TextView text;
//        public ImageView image;
//    }
}
