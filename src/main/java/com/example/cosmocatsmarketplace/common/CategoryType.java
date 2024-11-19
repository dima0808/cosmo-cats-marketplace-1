package com.example.cosmocatsmarketplace.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryType {

  FOOD("Food"),
  CLOTHES("Clothes"),
  ELECTRONICS("Electronics"),
  BOOKS("Books"),
  TOYS("Toys"),
  ACCESSORIES("Accessories"),
  OTHER("Other");

  private final String displayName;
}
