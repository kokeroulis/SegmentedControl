/*
 * Copyright (C) 2015 Antonis Tsiapaliokas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kokeroulis.gr.segmentedcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class SegmentedControl extends RadioGroup
    implements Observable.OnSubscribe<Integer> {

    public List<String> mItems = new ArrayList<>();
    private AttributeSet mAttrs;

    public SegmentedControl(Context context) {
        this(context, null);
    }

    public SegmentedControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public void setEntries(List<String> items) {
        mItems = items;

        for (int i = 0; i < mItems.size(); i++) {
            boolean hasLeftRadius = i == 0;
            boolean hasRightRadius = (i + 1) == mItems.size();

            SegmentedButton sb = new SegmentedButton(getContext(), mAttrs,
                                                     hasLeftRadius, hasRightRadius);
            sb.setId(i + 1);
            sb.setText(mItems.get(i));
            addView(sb);
        }

        invalidate();
    }

    public SegmentedButton findButtonById(int id) {
        return (SegmentedButton) findViewById(id);
    }

    public SegmentedButton findButtonBySlug(final String slug) {
        for (int i = 0; i< getChildCount(); i++) {
            SegmentedButton sb = (SegmentedButton) getChildAt(i);
            if (sb.getText().toString().equalsIgnoreCase(slug)) {
                return sb;
            }
        }
        return null;
    }

    public void setClickable(boolean clickable) {
        for (int i = 0; i< getChildCount(); i++) {
            SegmentedButton sb = (SegmentedButton) getChildAt(i);
            sb.setClickable(clickable);
        }
    }

    private void init(AttributeSet attrs) {
        mAttrs = attrs;
    }

    public Observable<Integer> selectionChanged() {
        return Observable.create(this);
    }

    @Override
    public void call(final Subscriber<? super Integer> subscriber) {
        SegmentedControl.this.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (subscriber != null && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(checkedId);
                }
            }
        });

        subscriber.add(new Subscription() {
            @Override
            public void unsubscribe() {
                SegmentedControl.this.setOnCheckedChangeListener(null);
            }

            @Override
            public boolean isUnsubscribed() {
                return subscriber != null && subscriber.isUnsubscribed();
            }
        });
    }
}
