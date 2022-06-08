# Crumb Trail

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
An app for reviewing grocery store products.

### App Evaluation
   - **Category:** Food / Social
   - **Mobile:** Mobile first experience (Reading reviews on the go), Camera used.
   - **Story:** Allows users to read reviews on grocery products to ensure they're buying something that's the right fit for them.
   - **Market:** Anyone that buys groceries and would like to know more about a product before buying it.
   - **Habit:** Users can review grocery products they buy for "points", which will give them higher levels and "review perks" (These perks still need to be defined). Users can be notified when an item is currently at a great deal by other users.
   - **Scope:** The smaller scope is an app to review products and view products reviews. The larger scope includes being notified of discounts and deals by other users, and being able to see where those deals are on the google maps sdk.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can post a new photo to their feed
* User can create a new account
* User can login
* User can search for other users
* User can view profiles
* User can follow/unfollow another user
* User can scan items
* User can view a feed of reviews

**Optional Nice-to-have Stories**

* User can leave comments on reviews
* User can view the google maps api and see stores
* User can see deals on the google maps api
* Users can mark deals on the google maps api

### 2. Screen Archetypes

* Registration screen
   * User can register for the app 
* Login Screen
   * User can login
* Profile Screen
   * User can view others or their own profiles
* Review Screen
   * User can view reviews for any product they click on
* Review Stream Screen
   * User can view a stream of reviews for a certain product
* Product Stream Screen
   * Users can see a stream of products that match their search results
* User Review Screen
   * Users can see a stream of users that match their search results (If they search with an @ before the text)
* Creation screen
   * User can create new reviews or reply to others (Compose) 
* Search
   * User can search for other users, products
   * User can follow/unfollow another user

### 3. Navigation

**Tab Navigation** (Tab to Screen)

  * View product/review stream
  * Search function (For reviews, users, and products)
  * View self profile

**Flow Navigation** (Screen to Screen)

* Registration Screen
  => Home
* Login Screen
  => Home
* Profile Screen
  => Self reviews
  => View who the user is following
  => View self liked products
  => View self liked reviews 
* Review Stream Screen
  => Click on a review to see a more detailed version of it
* Product Stream Screen
  => Click on a product to see the Review Stream for it 
* User Stream Screen
  => Click of a profile to see a more detailed version of it
* Creation screen
  => Home (After review is posted)
* Search
  => Product Stream
  => User Stream
