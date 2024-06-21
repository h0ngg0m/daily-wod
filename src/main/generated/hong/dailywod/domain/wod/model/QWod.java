package hong.dailywod.domain.wod.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import javax.annotation.processing.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

/** QWod is a Querydsl query type for Wod */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWod extends EntityPathBase<Wod> {

    private static final long serialVersionUID = 655561832L;

    public static final QWod wod = new QWod("wod");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt =
            createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    public final EnumPath<WodType> type = createEnum("type", WodType.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt =
            createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final DatePath<java.time.LocalDate> wodDate =
            createDate("wodDate", java.time.LocalDate.class);

    public QWod(String variable) {
        super(Wod.class, forVariable(variable));
    }

    public QWod(Path<? extends Wod> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWod(PathMetadata metadata) {
        super(Wod.class, metadata);
    }
}
