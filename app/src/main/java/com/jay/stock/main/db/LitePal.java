package com.jay.stock.main.db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.MapUtils;
import com.jay.stock.BuildConfig;
import com.jay.stock.main.App;
import com.jay.stock.main.db.base.ILitePalIDValue;
import com.tencent.wcdb.base.Value;
import com.tencent.wcdb.base.WCDBException;
import com.tencent.wcdb.core.Database;
import com.tencent.wcdb.core.Handle;
import com.tencent.wcdb.core.Table;
import com.tencent.wcdb.core.Transaction;
import com.tencent.wcdb.orm.Field;
import com.tencent.wcdb.orm.TableBinding;
import com.tencent.wcdb.winq.Column;
import com.tencent.wcdb.winq.Expression;
import com.tencent.wcdb.winq.Order;
import com.tencent.wcdb.winq.OrderingTerm;
import com.tencent.wcdb.winq.StatementSelect;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LitePal {

    private static Database getMainDbBase() {
        String dbPath = App.mContext.getDatabasePath("stock.db").getPath();
        return new Database(dbPath);
    }

    private static final Map<Class<?>, TableBinding<?>> mainTable = new HashMap<>();

    /**
     * 加载数据库
     */
    public static void loadLitePalDb() {
        try {
            //放入到主表
//            mainTable.put(AppCache.class, DBAppCache.INSTANCE);
            Database mainDbBase = getMainDbBase();
            MapUtils.forAllDo(mainTable, new MapUtils.Closure<Class<?>, TableBinding<?>>() {
                @Override
                public void execute(Class<?> key, TableBinding<?> value) {
                    mainDbBase.createTable(key.getSimpleName().toLowerCase(), value);
                }
            });
            Database.globalTraceException(new Database.ExceptionTracer() {
                @Override
                public void onTrace(@NonNull WCDBException exception) {
                    if (BuildConfig.DEBUG)
                        Log.e("LitePal", "Level:" + exception.level.toString() +
                                "--Message:" + exception.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

    @androidx.annotation.Nullable
    public static <T> TableBinding<T> getTableBinding(Class<T> tClass) {
        TableBinding<?> mainBinding = mainTable.get(tClass);
        if (mainBinding != null) {
            return (TableBinding<T>) mainBinding;
        }
        return null;
    }

    public static void deleteDatabase() {
        getMainDbBase().removeFiles();
    }

    public static <T extends ILitePalIDValue> void delete(TableBinding<T> binding, Expression expression) {
        LitePal.getMainOpera(binding)
                .delete(expression);
    }

    public static <T extends ILitePalIDValue> void deleteAll(TableBinding<T> binding) {
        LitePal.getMainOpera(binding)
                .deleteAll();
    }

    public static <T extends ILitePalIDValue> List<T> find(TableBinding<T> binding, Expression expression) {
        return LitePal.getMainOpera(binding)
                .find(expression);
    }

    public static <T extends ILitePalIDValue> List<T> findAll(TableBinding<T> binding) {
        return getMainOpera(binding)
                .findAll();
    }

    public static <T extends ILitePalIDValue> List<T> findEq(TableBinding<T> binding, String name, String value) {
        Column column = new Column(name);
        Expression expression = new Expression(column);
        return LitePal.getMainOpera(binding)
                .find(expression.eq(value));
    }

    public static <T extends ILitePalIDValue> T findFirst(TableBinding<T> binding, Expression expression) {
        return LitePal.getMainOpera(binding)
                .findFirst(expression);
    }

    public static <T extends ILitePalIDValue> T findFirstEq(TableBinding<T> binding, String name, String value) {
        Column column = new Column(name);
        Expression expression = new Expression(column);
        return LitePal.getMainOpera(binding)
                .findFirst(expression.eq(value));
    }

    public static <T extends ILitePalIDValue> T findFirst(TableBinding<T> binding) {
        return LitePal.getMainOpera(binding)
                .findFirst();
    }

    //查询一对一的表数据
    public static <R extends ILitePalIDValue, T extends ILitePalIDValue> T findOneToOneEntity(TableBinding<T> binding, R associationCls) {
        return LitePal.getMainOpera(binding)
                .findOneToOneEntity(associationCls);
    }

    //查询一对多的表数据
    public static <R extends ILitePalIDValue, T extends ILitePalIDValue> List<T> findOneToMainEntity(TableBinding<T> binding, R associationCls) {
        return LitePal.getMainOpera(binding)
                .findOneToMainEntity(associationCls);
    }

    public static <T extends ILitePalIDValue> void saveAll(TableBinding<T> binding, List<T> list) {
        LitePal.getMainOpera(binding)
                .saveOrUpdate(list);
    }

    public static <T extends ILitePalIDValue> void save(TableBinding<T> binding, T t) {
        LitePal.getMainOpera(binding)
                .saveOrUpdate(t);
    }

    public static <T extends ILitePalIDValue> boolean isExist(TableBinding<T> binding, Expression expression) {
        return LitePal.getMainOpera(binding).isExist(expression);
    }

    public static <T extends ILitePalIDValue> Opera<T> getMainOpera(TableBinding<T> binding) {
        return getOpera(getMainDbBase(), binding);
    }

    public static <T extends ILitePalIDValue> Opera<T> getOpera(Database database, TableBinding<T> binding) {
        return new Opera<>(database, binding);
    }


    //数据库操作封装
    public static class Opera<T extends ILitePalIDValue> {
        private Database database;
        private TableBinding<T> binding;

        public Opera(Database database, TableBinding<T> binding) {
            this.database = database;
            this.binding = binding;
        }

        @Nullable
        public T findFirst(@NotNull Expression condition) {
            try {
                return getTable().getFirstObject(condition);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Nullable
        public T findFirst(@NotNull Expression condition, OrderingTerm orderingTerm) {
            try {
                return getTable().getFirstObject(condition, orderingTerm);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public T findFirst() {
            try {
                return getTable().getFirstObject();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Nullable
        public T findLast(@NotNull Expression condition) {
            try {
                OrderingTerm orderBy = new OrderingTerm(new Column("id"))
                        .order(Order.Desc);
                return getTable().getFirstObject(condition, orderBy);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public T findLast() {
            try {
                OrderingTerm orderBy = new OrderingTerm(new Column("id"))
                        .order(Order.Desc);
                return getTable().getFirstObject(orderBy);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public List<T> findAll() {
            return getTable().getAllObjects();
        }

        public List<T> find(Expression expression, Field<T>[] fields) {
            return getTable().getAllObjects(fields, expression);
        }

        public List<T> find(Expression expression) {
            return getTable().getAllObjects(expression);
        }

        public List<T> find(Expression expression, OrderingTerm orderingTerm) {
            return getTable().getAllObjects(expression, orderingTerm);
        }

        public List<T> find(Expression expression, OrderingTerm orderingTerm, long limit) {
            return getTable().getAllObjects(expression, orderingTerm, limit);
        }

        public Table<T> getTable() {
            return database.getTable(binding.bindingType().getSimpleName().toLowerCase()
                    , binding);
        }

        public String getTabName() {
            return binding.bindingType().getSimpleName().toLowerCase();
        }


        /**
         * 保存或修改
         *
         * @param t
         * @return ID
         */
        public boolean saveOrUpdate(T t) {
            try {
                if (t.getLitePalID() == 0) {
                    getTable().insertObject(t);
                } else {
                    update(t);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 保存或修改
         * 通过条件判断是否是修改还是新增，而不是通过id
         *
         * @param t
         * @return ID
         */
        public boolean saveOrUpdate(T t, Expression expression) {
            try {
                T ts = findFirst(expression);
                if (ts == null) {
                    return saveOrUpdate(t);
                } else {
                    t.setLitePalID(ts.getLitePalID());
                    return update(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 不满足条件就存储
         * 比如某个包名存在就不继续保存了
         *
         * @param t
         * @return ID
         */
        public boolean saveOrIgnore(T t, Expression expression) {
            try {
                List<T> ts = find(expression);
                if (ts == null || ts.isEmpty()) {
                    return saveOrUpdate(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }


        public void saveOrUpdate(List<T> list) {
            try {
                List<T> insert = new ArrayList<>();
                List<T> update = new ArrayList<>();
                for (T item : list) {
                    if (item.getLitePalID() == 0) {
                        insert.add(item);
                    } else {
                        update.add(item);
                    }
                }
                if (!insert.isEmpty()) {
                    getTable().insertObjects(insert);
                } else if (!update.isEmpty()) {
                    database.runTransaction(new Transaction() {
                        @Override
                        public boolean insideTransaction(@NonNull Handle handle) throws WCDBException {
                            for (T item : update) {
                                update(item);
                            }
                            return true;
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public boolean update(T t) {
            try {
                if (t.getLitePalID() == 0) {
                    return false;
                }
                Column column = new Column("id");
                Expression expression = new Expression(column);
                Expression eq = expression.eq(t.getLitePalID());
                getTable().updateObject(t, eq);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        //更新局部字段
        public boolean update(T t, Field<T> field) {
            try {
                if (t.getLitePalID() == 0) {
                    return false;
                }
                Column column = new Column("id");
                Expression expression = new Expression(column);
                Expression eq = expression.eq(t.getLitePalID());
                getTable().updateObject(t, field, eq);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        //更新局部字段
        public boolean update(T t, Field<T>[] field) {
            try {
                if (t.getLitePalID() == 0) {
                    return false;
                }
                Column column = new Column("id");
                Expression expression = new Expression(column);
                Expression eq = expression.eq(t.getLitePalID());
                getTable().updateObject(t, field, eq);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        //更新局部字段
        public boolean update(T t, Field<T> field, Expression expression) {
            try {
                getTable().updateObject(t, field, expression);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        //更新局部字段
        public boolean update(T t, Field<T>[] field, Expression expression) {
            try {
                getTable().updateObject(t, field, expression);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean delete(Expression expression) {
            try {
                getTable().deleteObjects(expression);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean deleteAll() {
            try {
                getTable().deleteObjects();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean isExist(String key, String values) {
            Column column = new Column(key);
            Expression expression = new Expression(column);
            return isExist(expression.eq(values));
        }

        public boolean isExist(Expression expression) {
            return findFirst(expression) != null;
        }

        public <R extends ILitePalIDValue> boolean delete(List<R> list) {
            boolean result = true;
            for (R t : list) {
                Column column = new Column("id");
                Expression expression = new Expression(column);
                Expression eq = expression.eq(t.getLitePalID());
                boolean delete = delete(eq);
                if (!delete) {
                    result = false;
                }
            }
            return result;
        }

        //获取有多少条数据
        public long count() {
            Column column = new Column("id");
//            getTable().getOneColumn(column);
            // 获取不同的 content 数
            Value count = database.getValueFromStatement(
                    new StatementSelect().select(column.count())
                            .from(getTabName()));
            if (count != null) {
                return count.getLong();
            } else {
                return 0;
            }
        }


        //查询一对一的表数据
        public <R extends ILitePalIDValue> T findOneToOneEntity(R associationCls) {
            Column column = new Column(getTabName().toLowerCase() + "_" +
                    associationCls.getClass().getSimpleName().toLowerCase() + "_id");
            Expression expression = new Expression(column);
            Expression eq = expression.eq(associationCls.getLitePalID());
            return findFirst(eq);
        }

        //查询一对多的表数据
        public <R extends ILitePalIDValue> List<T> findOneToMainEntity(R associationCls) {
            Column column = new Column(getTabName().toLowerCase() + "_" +
                    associationCls.getClass().getSimpleName().toLowerCase() + "_id");
            Expression expression = new Expression(column);
            Expression eq = expression.eq(associationCls.getLitePalID());
            return find(eq);
        }
    }
}