package hong.dailywod.domain.wodhistory.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import javax.annotation.processing.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.PathInits;

/** QWodHistory is a Querydsl query type for WodHistory */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWodHistory extends EntityPathBase<WodHistory> {

    private static final long serialVersionUID = -1223788810L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWodHistory wodHistory = new QWodHistory("wodHistory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath record = createString("record");

    public final hong.dailywod.domain.user.model.QUser user;

    public final hong.dailywod.domain.wod.model.QWod wod;

    public QWodHistory(String variable) {
        this(WodHistory.class, forVariable(variable), INITS);
    }

    public QWodHistory(Path<? extends WodHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWodHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWodHistory(PathMetadata metadata, PathInits inits) {
        this(WodHistory.class, metadata, inits);
    }

    public QWodHistory(Class<? extends WodHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user =
                inits.isInitialized("user")
                        ? new hong.dailywod.domain.user.model.QUser(forProperty("user"))
                        : null;
        this.wod =
                inits.isInitialized("wod")
                        ? new hong.dailywod.domain.wod.model.QWod(forProperty("wod"))
                        : null;
    }
}
