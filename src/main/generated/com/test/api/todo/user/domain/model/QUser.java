package com.test.api.todo.user.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1784946524L;

    public static final QUser user = new QUser("user");

    public final com.test.api.todo.boot.domain.QBaseEntity _super = new com.test.api.todo.boot.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDTime = _super.createdDTime;

    public final StringPath crn = createString("crn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDTime = _super.modifiedDTime;

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    //inherited
    public final NumberPath<Integer> status = _super.status;

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

