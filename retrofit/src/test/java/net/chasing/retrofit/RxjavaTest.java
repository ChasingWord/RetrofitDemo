package net.chasing.retrofit;

import android.os.Handler;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.flowables.GroupedFlowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sangfor on 2016/11/10.
 */
public class RxjavaTest {
    public void l(String log) {
        System.out.println(log);
    }

    public void l(int log) {
        System.out.println(log);
    }

    /**
     * 不懂
     */
    private void join() {
        Flowable.just(11, 22).join(Flowable.just(33, 44), new Function<Integer, Flowable<Integer>>() {
            @Override
            public Flowable<Integer> apply(Integer integer) throws Exception {
                return Flowable.just(integer);
            }
        }, new Function<Integer, Flowable<Integer>>() {
            @Override
            public Flowable<Integer> apply(Integer integer) throws Exception {
                return Flowable.just(integer);
            }
        }, new BiFunction<Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2 + "";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                l(s);
            }
        });
    }

    private void sample() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 20; i++) {
                    e.onNext(i);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e1e) {
                        e1e.printStackTrace();
                    }
                }
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.newThread()).sample(5000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        l(integer);
                    }
                });
    }

    private void elementAt() {
        Flowable.just(1, 2, 3, 4).elementAt(3).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                l(integer);
            }
        });
    }

    /**
     * 不懂
     */
    private void debounce() {
        Flowable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).debounce(new Function<Integer, Flowable<Integer>>() {
            @Override
            public Flowable<Integer> apply(final Integer integer) throws Exception {
                l(integer);
                return Flowable.create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                        if (integer % 2 == 0) {
                            l("complete:" + integer);
                            e.onNext(integer);
                            e.onComplete();
                        }
                    }
                }, BackpressureStrategy.BUFFER);
            }
        })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        l("get " + integer);
                    }
                });
    }

    private void ignorElements() {
        Flowable.just(12, 2).ignoreElements().subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void last() {
        Flowable.just(1, 2, 3).last(2);
    }

    private void first() {
        Flowable.just(1, 2, 3).firstElement().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                l(integer);
            }
        });
    }

    public void merge() {
        Flowable.merge(Flowable.just(1, 2), Flowable.just("2", "1"))
                .subscribe(new Consumer<Serializable>() {
                    @Override
                    public void accept(Serializable serializable) throws Exception {
                        if (serializable.getClass().getSimpleName().equals("Integer"))
                            l((Integer) serializable);
                    }
                });
    }

    private void window() {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(1);
        a.add(4);
        Flowable.fromIterable(a).window(3000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Flowable<Integer>>() {
                    @Override
                    public void accept(Flowable<Integer> integerFlowable) throws Exception {
                        integerFlowable.subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                l(integer + "window");
                            }
                        });
                    }
                });
    }

    public void groupBy() {
        Flowable.just(1, 5, 22, 3, 2).groupBy(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                if (integer % 2 == 1) {
                    return "奇数";
                }
                return "偶数";
            }
        }).toList().subscribe(new Consumer<List<GroupedFlowable<String, Integer>>>() {
            @Override
            public void accept(List<GroupedFlowable<String, Integer>> groupedFlowables) throws Exception {
                for (int i = 0; i < groupedFlowables.size(); i++) {
                    l(groupedFlowables.get(i).getKey());
                    Iterable<Integer> integers = groupedFlowables.get(i).blockingIterable();
                    for (Integer integer : integers) {
                        l(integer);
                    }
                }
            }
        });
    }

    private void empty() {
        Flowable.empty().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                l("empty");
            }
        });
    }

    private void never() {
        Flowable.never().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                l("never");
            }
        });
    }

    private void scan() {
//        Flowable.fromArray(1,2,3,4,5,6).scan(new BiFunction<Integer, Integer, Integer>() {
//            @Override
//            public Integer apply(Integer integer, Integer integer2) throws Exception {
//                return integer * integer2;
//            }
//        }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                l(integer + "");
//            }
//        });

        ArrayList<Integer> a = new ArrayList<>();
        a.add(2);
        a.add(2);
        a.add(3);
        Flowable.fromIterable(a).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer * integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                l(integer + "");
            }
        });
    }

    private void reduce() {
        Flowable.just(1, 23, 41, 1, 2, 41).reduce(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                l(integer + "");
            }
        });
    }

    private void buffer() {
        Flowable.just(1, 2, 3, 4, 5).buffer(2, 3).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                l(integers + "buffer");
            }
        });
    }

    private void distinct() {
        Flowable.just(1, 2, 2, 3, 1, 1, 5, 6).distinct().skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        l(integer + "");
                    }
                });
    }

    private void future() {
        Flowable.fromFuture(new Future<String>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        });
    }

    private void zip() {
        Flowable.zip(Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("1");
            }
        }, BackpressureStrategy.BUFFER), Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("2");
            }
        }, BackpressureStrategy.BUFFER), new BiFunction<String, String, String>() {

            @Override
            public String apply(String sn, String s) throws Exception {
                return sn + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                l(s);
            }
        });
    }

    private void startWith() {
        Flowable.just(1).startWith(100).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                l(integer + "");
            }
        });
    }

    /**
     * 没起作用。。
     */
    private void interval() {
        Flowable<Long> longFlowable = Flowable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
        Subscriber<Long> subscriber = new Subscriber<Long>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Long integer) {
                l(integer + "");
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                l("onComplete");
            }
        };
        longFlowable.subscribe(subscriber);
    }

    private void concatMap() {
        Flowable.fromArray("a")
                .concatMap(new Function<String, Flowable<String>>() {
                    @Override
                    public Flowable<String> apply(String s) throws Exception {
                        String s1 = s + " one";
                        return Flowable.fromArray(s1);
                    }
                })
                .concatMap(new Function<String, Flowable<String>>() {
                    @Override
                    public Flowable<String> apply(String s) throws Exception {
                        return Flowable.fromArray(s + "two");
                    }
                })
                .firstElement()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        l(s);
                    }
                });
    }

    private void defer() {
        l("before: " + System.currentTimeMillis());
        final Flowable<String> flowable = Flowable.defer(new Callable<Flowable<String>>() {
            @Override
            public Flowable<String> call() throws Exception {
                return Flowable.just(System.currentTimeMillis() + "");
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                flowable.subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        l(s);
                    }
                });
            }
        }, 2000);
    }

    private void concat() {
        Flowable.concat(Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER), Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("two");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER), Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
//                e.onNext("three");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER), Flowable.just("four")).firstElement()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        l(s);
                    }
                });
    }

    private void map() {
        Flowable.range(2, 5)
                .flatMap(new Function<Integer, Flowable<String>>() {
                    @Override
                    public Flowable<String> apply(Integer integer) throws Exception {
                        return Flowable.fromArray(integer + " 1");
                    }
                })
//                .map(new Function<Integer, String>() {
//                    @Override
//                    public String apply(Integer integer) throws Exception {
//                        return integer + "";
//                    }
//                })
                .first("")
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        l(s);
                    }
                });
    }

    private void combineLast() {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 4; i++) {
                    e.onNext(i);
                    Thread.sleep(1000);
                }
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.newThread());
        Flowable.combineLatest(flowable, flowable, new BiFunction<Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2) throws Exception {
                l("left: " + integer + " right: " + integer2);
                return integer + integer2 + "";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                l(s);
            }
        });
    }
}
