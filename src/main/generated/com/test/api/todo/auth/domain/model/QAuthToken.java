package com.test.api.todo.auth.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthToken is a Querydsl query type for AuthToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthToken extends EntityPathBase<AuthToken> {

    private static final long serialVersionUID = -1356736061L;

    public static final QAuthToken authToken = new QAuthToken("authToken");

    public final com.test.api.todo.boot.domain.QBaseEntity _super = new com.test.api.todo.boot.domain.QBaseEntity(this);

    public final StringPath accessToken = createString("accessToken");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDTime = _super.createdDTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDTime = _super.modifiedDTime;

    public final StringPath refreshToken = createString("refreshToken");

    public final StringPath seq = createString("seq");

    //inherited
    public final NumberPath<Integer> status = _super.status;

    public final NumberPath<Long> userSeq = createNumber("userSeq", Long.class);

    public QAuthToken(String variable) {
        super(AuthToken.class, forVariable(variable));
    }

    public QAuthToken(Path<? extends AuthToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthToken(PathMetadata metadata) {
        super(AuthToken.class, metadata);
    }

}

