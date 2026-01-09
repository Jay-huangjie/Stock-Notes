package com.jay.stock.main.db.base;

import android.util.Log;

import com.jay.stock.main.db.LitePal;
import com.tencent.wcdb.orm.Field;
import com.tencent.wcdb.orm.TableBinding;
import com.tencent.wcdb.winq.Column;
import com.tencent.wcdb.winq.Expression;

import java.util.List;

/**
 * create by hj on 2022/10/19
 * 关联id定义规则： 一对一 当前表_关联表_id
 * 一对多： 当前表_对多表_id
 **/
public abstract class BaseLitePal<T extends ILitePalIDValue> implements ILitePalIDValue {

    public static final String TAG = "LitePal";

    public abstract TableBinding<T> getTabBinding();

    protected abstract T getTabObject();

    protected LitePal.Opera<T> getOpera() {
        return LitePal.getMainOpera(getTabBinding());
    }

    //保存或修改
    public boolean save() {
        try {
            if (checkVerification()) {
                return getOpera()
                        .saveOrUpdate(getTabObject());
            }
        } catch (Exception e) {
            log(e.getMessage());
        }
        return false;
    }


    private boolean checkVerification() {
        TableBinding<T> tabBinding = getTabBinding();
        if (tabBinding == null) {
            log("未获取到tabBinding" + getClass().getSimpleName());
            return false;
        }
        LitePal.Opera<T> opera = getOpera();
        if (opera == null) {
            log("未获取到opera" + getClass().getSimpleName());
            return false;
        }
        return true;
    }

    //查询一对一的表数据
    public <R extends ILitePalIDValue> T findOneToOneEntity(R associationCls) {
        Column column = new Column(getTabName().toLowerCase() + "_" +
                associationCls.getClass().getSimpleName().toLowerCase() + "_id");
        Expression expression = new Expression(column);
        Expression eq = expression.eq(associationCls.getLitePalID());
        return getOpera().findFirst(eq);
    }

    public boolean update(long id) {
        try {
            if (checkVerification()) {
                if (id == 0) {
                    log("update 失败,id为0");
                    return false;
                }
                setLitePalID(id);
                getOpera().update(getTabObject());
                return true;
            }
        } catch (Exception e) {
            log(e.getMessage());
        }
        return false;
    }

    //更新所有的参数
    public boolean update() {
        try {
            if (checkVerification()) {
                if (getLitePalID() == 0) {
                    log("update 失败,id为0");
                    return false;
                }
                getOpera().update(getTabObject());
                return true;
            }
        } catch (Exception e) {
            log(e.getMessage());
        }
        return false;
    }

    //更新局部的参数
    public boolean update(Field<T> field) {
        try {
            if (checkVerification()) {
                if (getLitePalID() == 0) {
                    log("update 失败,id为0");
                    return false;
                }
                getOpera().update(getTabObject(), field);
                return true;
            }
        } catch (Exception e) {
            log(e.getMessage());
        }
        return false;
    }

    //更新局部的参数
    public boolean update(Field<T>[] field) {
        try {
            if (checkVerification()) {
                if (getLitePalID() == 0) {
                    log("update 失败,id为0");
                    return false;
                }
                getOpera().update(getTabObject(), field);
                return true;
            }
        } catch (Exception e) {
            log(e.getMessage());
        }
        return false;
    }

    public boolean delete(Expression expression) {
        try {
            if (checkVerification()) {
                getOpera().delete(expression);
                return true;
            }
        } catch (Exception e) {
            log(e.getMessage());
        }
        return false;
    }

    //删除这个类的数据
    public boolean delete() {
        if (getLitePalID() == 0) {
            return false;
        }
        Expression expression = new Expression(new Column("id"));
        Expression eq = expression.eq(getLitePalID());
        return delete(eq);
    }

    public <R extends ILitePalIDValue> boolean delete(List<R> list) {
        return getOpera()
                .delete(list);
    }

    public String getTabName() {
        return getTabObject().getClass().getSimpleName();
    }

    public void log(String message) {
        if (message != null) {
            Log.d(TAG, message);
        }
    }
}
