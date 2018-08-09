package com.meganet.eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class WordServiceImpl implements WordService{
	
	@Autowired VerbClient verbClient;
	@Autowired SubjectClient subjectClient;
	@Autowired ArticleClient articleClient;
	@Autowired AdjectiveClient adjectiveClient;
	@Autowired NounClient nounClient;
	
	
	@Override
	@HystrixCommand(fallbackMethod="getFallbackSubject")
	public Word getSubject() {
		return new Word(subjectClient.getWord());
	}
	
	@Override
	public Word getVerb() {
		return new Word(verbClient.getWord());
	}
	
	@Override
	public Word getArticle() {
		return new Word(articleClient.getWord());
	}
	
	@Override
	@HystrixCommand(fallbackMethod="getFallbackAdjective")
	public Word getAdjective() {
		return new Word(adjectiveClient.getWord());
	}
	
	@Override
	@HystrixCommand(fallbackMethod="getFallbackNoun")
	public Word getNoun() {
		return new Word(nounClient.getWord());
	}	

	public Word getFallbackSubject() {
		return new Word("Someone");
	}
	
	public Word getFallbackAdjective() {
		return new Word("");
	}
	
	public Word getFallbackNoun() {
		return new Word("something");
	}

}
