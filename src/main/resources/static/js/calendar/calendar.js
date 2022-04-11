'use strict';
// Date 객체 생성
let date = new Date();

const renderCalendar = () => {
  const viewYear = date.getFullYear();
  const viewMonth = date.getMonth();

  // year-month 채우기
  document.querySelector('.year-month').textContent = `${viewYear}년 ${viewMonth + 1}월`;

  // 지난 달 마지막 Date, 이번 달 마지막 Date
  const prevLast = new Date(viewYear, viewMonth, 0);
  const thisLast = new Date(viewYear, viewMonth + 1, 0);

  const PLDate = prevLast.getDate();
  const PLDay = prevLast.getDay();

  const TLDate = thisLast.getDate();
  const TLDay = thisLast.getDay();

  // Dates 기본 배열들
  const prevDates = [];
  const thisDates = [...Array(TLDate + 1).keys()].slice(1);
  const nextDates = [];

  // prevDates 계산
  if (PLDay !== 6) {
    for (let i = 0; i < PLDay + 1; i++) {
      prevDates.unshift(PLDate - i);
    }
  }

  // nextDates 계산
  for (let i = 1; i < 7 - TLDay; i++) {
    nextDates.push(i)
  }

  // Dates 합치기
  const dates = prevDates.concat(thisDates, nextDates);

  // Dates 정리
  const firstDateIndex = dates.indexOf(1);
  // console.log("첫날 인덱스:"+firstDateIndex);//첫날 인덱스
  const lastDateIndex = dates.lastIndexOf(TLDate);
  // console.log("마지막날 인덱스:"+lastDateIndex);//마지막날 인덱스
  dates.forEach((date, i) => {
    console.log("date:"+date);
    console.log("i:"+i);
    const condition = i >= firstDateIndex && i < lastDateIndex + 1
                      ? 'this'
                      : 'other';

    if(condition == 'this'){
      console.log(i+"는 this");
      dates[i] = `<div class="date thisM ${date}"><span class="${condition}">${date}</span></div>`;
    }else if(condition == 'other'){
      console.log(i+"는 other");
      dates[i] = `<div class="date otherM ${date}"><span class="${condition}">${date}</span></div>`;
    }
  })

  // Dates 그리기
  document.querySelector('.dates').innerHTML = dates.join('');

    // 오늘 날짜 그리기
    const today = new Date();
    if (viewMonth === today.getMonth() && viewYear === today.getFullYear()) {
      for (let date of document.querySelectorAll('.this')) {
        if (+date.innerText === today.getDate()) {
          date.classList.add('today');
          break;
        }
      }
    }
}

renderCalendar();

const prevMonth = () => {
  date.setMonth(date.getMonth() - 1);
  renderCalendar();
}

const nextMonth = () => {
  date.setMonth(date.getMonth() + 1);
  renderCalendar();
}

const goToday = () => {
  date = new Date();
  renderCalendar();
}