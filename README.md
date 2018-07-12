# WingCommonLib
CommonLib

How to
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.WnagYo:WingCommonLib:v1.0.0'
	}
Share this release:

#### How to use.

```java
/**
 * Created by Wing-Ti on 2018/7/10.
 * Time： 15:27
 * ClassNote：
 */
public class ServerApi {
    public static Observable<String> getString(String start, String borrow_type) {
        return OkGo.<String>post(Urls.POST_PROPERTY)
                .params("start", start)
                .params("borrow_type", borrow_type)
                .converter(new StringConvert())
                .adapt(new ObservableBody<String>());
    }

    public static Observable<ResponseData<List<BorrowListBean>>> getData() {
        return OkGo.<ResponseData<List<BorrowListBean>>>post(Urls.POST_PROPERTY)
                .params("start", 1)
                .params("borrow_type", "all")
                .converter(new JsonConvert<ResponseData<List<BorrowListBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<BorrowListBean>>>());
    }
}
```



```java
ServerApi.getData()
        .compose(RxSchedulersHelper.<ResponseData<List<BorrowListBean>>>io_main())
        .compose(RxResultHelper.<List<BorrowListBean>>handleResult())
        .subscribe(new RxObserver<List<BorrowListBean>>() {
            @Override
            public void _onNext(List<BorrowListBean> beans) {
                Log.e(TAG, "_onNext: " + beans.size());
            }

            @Override
            public void _onError(String errorMessage) {
                Log.e(TAG, "_onError: " + errorMessage);
            }
        });
ServerApi.getString("1", "all")
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                Log.e(TAG, "accept: ");
            }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: " + s);
                tvMain.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        });
```