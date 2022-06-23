import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubProjectAttrDetailComponent } from './sub-project-attr-detail.component';

describe('SubProjectAttr Management Detail Component', () => {
  let comp: SubProjectAttrDetailComponent;
  let fixture: ComponentFixture<SubProjectAttrDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SubProjectAttrDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ subProjectAttr: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SubProjectAttrDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SubProjectAttrDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load subProjectAttr on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.subProjectAttr).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
