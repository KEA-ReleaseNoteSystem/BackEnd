package kakao99.backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemo is a Querydsl query type for Memo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemo extends EntityPathBase<Memo> {

    private static final long serialVersionUID = -114404128L;

    public static final QMemo memo = new QMemo("memo");

    public final DateTimePath<java.util.Date> createdAt = createDateTime("createdAt", java.util.Date.class);

    public final DateTimePath<java.util.Date> deletedAt = createDateTime("deletedAt", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isActive = createBoolean("isActive");

    public final NumberPath<Issue> issue = createNumber("issue", Issue.class);

    public final NumberPath<Member> member = createNumber("member", Member.class);

    public final StringPath memo_content = createString("memo_content");

    public final DateTimePath<java.util.Date> updatedAt = createDateTime("updatedAt", java.util.Date.class);

    public QMemo(String variable) {
        super(Memo.class, forVariable(variable));
    }

    public QMemo(Path<? extends Memo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemo(PathMetadata metadata) {
        super(Memo.class, metadata);
    }

}

