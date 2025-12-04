package ktb3.full.community.presentation.ratelimiter;

import ktb3.full.community.stub.StubTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TokenBucketTest {

    @Nested
    class create {

        @Test
        void 버킷_생성_시_용량만큼_토큰이_생성된다() {
            // given
            StubTime time = new StubTime(LocalDateTime.of(2025, 12, 4, 20, 0, 0));
            long capacity = 1000;

            // when
            TokenBucket tokenBucket = TokenBucket.create(capacity, 0, 1, time);

            // then
            assertThat(tokenBucket.getTokens()).isEqualTo(capacity);
        }
    }

    @Nested
    class tryConsume {

        @Test
        void 버킷에_토큰이_남아있으면_하나를_소비한다() {
            // given
            StubTime time = new StubTime(LocalDateTime.of(2025, 12, 4, 20, 0, 0));

            TokenBucket sut = TokenBucket.create(1, 0, 1, time);

            // when
            boolean isPermitted = sut.tryConsume();

            // then
            assertThat(isPermitted).isTrue();
        }

        @Test
        void 버킷에_토큰이_남아있지_않으면_소비하지_못한다() {
            // given
            StubTime time = new StubTime(LocalDateTime.of(2025, 12, 4, 20, 0, 0));

            TokenBucket sut = TokenBucket.create(0, 0, 1, time);

            // when
            boolean isPermitted = sut.tryConsume();

            // then
            assertThat(isPermitted).isFalse();
        }
    }
}