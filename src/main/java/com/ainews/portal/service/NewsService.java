package com.ainews.portal.service;

import com.ainews.portal.entity.News;
import com.ainews.portal.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> getTodayNews() {
        return newsRepository.findByNewsDateOrderByHeatScoreDesc(LocalDate.now());
    }

    public List<News> getNewsByDate(LocalDate date) {
        return newsRepository.findByNewsDateOrderByHeatScoreDesc(date);
    }

    public List<News> getTopNewsByDate(LocalDate date, int limit) {
        return newsRepository.findTopByDate(date, PageRequest.of(0, limit));
    }

    public News saveNews(News news) {
        return newsRepository.save(news);
    }

    public void initializeSampleData() {
        if (newsRepository.count() == 0) {
            LocalDate today = LocalDate.now();

            News news1 = new News();
            news1.setTitle("OpenAI发布GPT-5：AGI领域重大突破");
            news1.setSource("TechCrunch");
            news1.setUrl("https://techcrunch.com/ai/gpt5-release");
            news1.setHeatScore(new BigDecimal("98.5"));
            news1.setPublishTime(LocalDateTime.now().minusHours(2));
            news1.setSummary("OpenAI今日正式发布GPT-5，在AGI领域取得重大突破。新模型展现出前所未有的推理能力和多模态理解能力，引发业界广泛关注。");
            news1.setNewsDate(today);
            newsRepository.save(news1);

            News news2 = new News();
            news2.setTitle("谷歌DeepMind推出Gemini Ultra 2.0");
            news2.setSource("The Verge");
            news2.setUrl("https://verge.com/ai/gemini-ultra-2");
            news2.setHeatScore(new BigDecimal("95.2"));
            news2.setPublishTime(LocalDateTime.now().minusHours(4));
            news2.setSummary("谷歌DeepMind团队今日宣布推出Gemini Ultra 2.0，该版本在代码生成和数学推理方面性能大幅提升，直接对标GPT-5。");
            news2.setNewsDate(today);
            newsRepository.save(news2);

            News news3 = new News();
            news3.setTitle("Anthropic融资30亿美元估值达600亿");
            news3.setSource("Bloomberg");
            news3.setUrl("https://bloomberg.com/ai/anthropic-funding");
            news3.setHeatScore(new BigDecimal("92.8"));
            news3.setPublishTime(LocalDateTime.now().minusHours(6));
            news3.setSummary("AI安全公司Anthropic完成新一轮30亿美元融资，公司估值达到600亿美元，成为仅次于OpenAI的第二大AI独角兽。");
            news3.setNewsDate(today);
            newsRepository.save(news3);

            News news4 = new News();
            news4.setTitle("Meta开源Llama 3.5：性能超越GPT-4");
            news4.setSource("Wired");
            news4.setUrl("https://wired.com/ai/llama-3-5");
            news4.setHeatScore(new BigDecimal("89.5"));
            news4.setPublishTime(LocalDateTime.now().minusHours(8));
            news4.setSummary("Meta正式开源Llama 3.5系列模型，在多项基准测试中超越GPT-4，且完全免费商用，引发开源社区热烈讨论。");
            news4.setNewsDate(today);
            newsRepository.save(news4);

            News news5 = new News();
            news5.setTitle("英伟达发布下一代AI芯片Blackwell Ultra");
            news5.setSource("Ars Technica");
            news5.setUrl("https://arstechnica.com/ai/nvidia-blackwell-ultra");
            news5.setHeatScore(new BigDecimal("86.3"));
            news5.setPublishTime(LocalDateTime.now().minusHours(10));
            news5.setSummary("英伟达在GTC大会上发布Blackwell Ultra芯片，AI训练性能提升3倍，能效比大幅优化，将于下半年正式出货。");
            news5.setNewsDate(today);
            newsRepository.save(news5);

            // Add some historical data for yesterday
            LocalDate yesterday = today.minusDays(1);
            News histNews1 = new News();
            histNews1.setTitle("微软Copilot全面集成GPT-5");
            histNews1.setSource("Microsoft Blog");
            histNews1.setUrl("https://microsoft.com/ai/copilot-gpt5");
            histNews1.setHeatScore(new BigDecimal("94.0"));
            histNews1.setPublishTime(yesterday.atTime(10, 30));
            histNews1.setSummary("微软宣布Copilot全面集成GPT-5技术，Windows、Office全家桶将获得AI能力大幅提升。");
            histNews1.setNewsDate(yesterday);
            newsRepository.save(histNews1);

            News histNews2 = new News();
            histNews2.setTitle("特斯拉Optimus机器人实现量产");
            histNews2.setSource("Reuters");
            histNews2.setUrl("https://reuters.com/ai/tesla-optimus");
            histNews2.setHeatScore(new BigDecimal("91.5"));
            histNews2.setPublishTime(yesterday.atTime(14, 0));
            histNews2.setSummary("特斯拉宣布Optimus人形机器人正式量产，首批1000台将部署在工厂中，预计2025年面向消费者发售。");
            histNews2.setNewsDate(yesterday);
            newsRepository.save(histNews2);
        }
    }
}
